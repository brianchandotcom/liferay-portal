/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.content.dashboard.document.library.internal.item.provider;

import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.document.library.display.context.DLMimeTypeDisplayContext;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Alejandro Tardín
 * @author Cristina González
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.DLConfiguration",
	service = FileExtensionGroupsProvider.class
)
public class FileExtensionGroupsProvider {

	public List<FileExtensionGroup> getFileExtensionGroups() {
		return _fileExtensionGroups;
	}

	public boolean isOther(String extension) {
		return !_extensionMimeTypes.containsKey(extension);
	}

	public static class FileExtensionGroup
		implements Comparable<FileExtensionGroup> {

		public FileExtensionGroup(
			String[] extensions, String key, String[] mimeTypes) {

			_extensions = extensions;
			_key = key;
			_mimeTypes = mimeTypes;
		}

		@Override
		public int compareTo(FileExtensionGroup fileExtensionGroup) {
			if (Objects.equals(_OTHER, fileExtensionGroup._key)) {
				return -1;
			}
			else if (Objects.equals(_OTHER, _key)) {
				return 1;
			}

			return _key.compareTo(fileExtensionGroup.getKey());
		}

		public String getKey() {
			return _key;
		}

		public JSONObject toJSONObject(
			Set<String> checkedFileExtensions,
			List<String> filterFileExtensions,
			DLMimeTypeDisplayContext dlMimeTypeDisplayContext,
			Language language, Locale locale, Set<String> otherFileExtensions) {

			Stream<String> stream = filterFileExtensions.stream();

			String[] extensions = stream.filter(
				extension -> {
					if (!Objects.equals(_key, _OTHER)) {
						return ArrayUtil.contains(_extensions, extension);
					}

					return otherFileExtensions.contains(extension);
				}
			).toArray(
				String[]::new
			);

			if (ArrayUtil.isEmpty(extensions)) {
				return null;
			}

			return JSONUtil.put(
				"fileExtensions",
				JSONUtil.putAll(
					Stream.of(
						extensions
					).map(
						fileExtension -> JSONUtil.put(
							"fileExtension", fileExtension
						).put(
							"selected",
							checkedFileExtensions.contains(fileExtension)
						)
					).toArray())
			).put(
				"icon", _getIcon(dlMimeTypeDisplayContext)
			).put(
				"iconCssClass", _getIconCssClass(dlMimeTypeDisplayContext)
			).put(
				"label", language.get(locale, getKey())
			);
		}

		private String _getIcon(
			DLMimeTypeDisplayContext dlMimeTypeDisplayContext) {

			if (ArrayUtil.isEmpty(_extensions)) {
				return "document-default";
			}

			return dlMimeTypeDisplayContext.getIconFileMimeType(_mimeTypes[0]);
		}

		private String _getIconCssClass(
			DLMimeTypeDisplayContext dlMimeTypeDisplayContext) {

			if (ArrayUtil.isEmpty(_extensions)) {
				return null;
			}

			return dlMimeTypeDisplayContext.getCssClassFileMimeType(
				_mimeTypes[0]);
		}

		private final String[] _extensions;
		private final String _key;
		private final String[] _mimeTypes;

	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) throws Exception {
		_extensionMimeTypes = new HashMap<>();

		_dlConfiguration = ConfigurableUtil.createConfigurable(
			DLConfiguration.class, properties);

		Set<Map.Entry<String, Function<DLConfiguration, String[]>>> entries =
			_fileExtensionMimetype.entrySet();

		Stream<Map.Entry<String, Function<DLConfiguration, String[]>>> stream =
			entries.stream();

		_fileExtensionGroups = stream.map(
			entry -> _createFileExtensionGroup(entry.getKey(), entry.getValue())
		).sorted(
		).collect(
			Collectors.toList()
		);
	}

	private FileExtensionGroup _createFileExtensionGroup(
		String key, Function<DLConfiguration, String[]> function) {

		String[] mimeTypes = function.apply(_dlConfiguration);

		Stream<String> stream = Arrays.stream(mimeTypes);

		String[] extensions = stream.flatMap(
			mimeType -> {
				Set<String> extensionsMimeTypes = MimeTypesUtil.getExtensions(
					mimeType);

				for (String extensionsMimeType : extensionsMimeTypes) {
					_extensionMimeTypes.put(
						extensionsMimeType.replaceAll("^\\.", StringPool.BLANK),
						mimeType);
				}

				return extensionsMimeTypes.stream();
			}
		).map(
			extension -> extension.replaceAll("^\\.", StringPool.BLANK)
		).sorted(
		).toArray(
			String[]::new
		);

		return new FileExtensionGroup(extensions, key, mimeTypes);
	}

	private static final String _OTHER = "other";

	private static final Map<String, Function<DLConfiguration, String[]>>
		_fileExtensionMimetype =
			HashMapBuilder.<String, Function<DLConfiguration, String[]>>put(
				_OTHER, dlConfiguration -> new String[0]
			).put(
				"audio",
				dlConfiguration ->
					PropsValues.DL_FILE_ENTRY_PREVIEW_AUDIO_MIME_TYPES
			).put(
				"code", dlConfiguration -> dlConfiguration.codeFileMimeTypes()
			).put(
				"compressed",
				dlConfiguration -> dlConfiguration.compressedFileMimeTypes()
			).put(
				"image",
				dlConfiguration ->
					PropsValues.DL_FILE_ENTRY_PREVIEW_IMAGE_MIME_TYPES
			).put(
				"presentation",
				dlConfiguration -> dlConfiguration.presentationFileMimeTypes()
			).put(
				"spreadsheet",
				dlConfiguration -> dlConfiguration.spreadSheetFileMimeTypes()
			).put(
				"text", dlConfiguration -> dlConfiguration.textFileMimeTypes()
			).put(
				"vectorial",
				dlConfiguration -> dlConfiguration.vectorialFileMimeTypes()
			).put(
				"video",
				dlConfiguration ->
					PropsValues.DL_FILE_ENTRY_PREVIEW_VIDEO_MIME_TYPES
			).build();

	private volatile DLConfiguration _dlConfiguration;
	private volatile Map<String, String> _extensionMimeTypes;
	private volatile List<FileExtensionGroup> _fileExtensionGroups =
		new ArrayList<>();

}