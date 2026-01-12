/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.headless.admin.site.client.dto.v1_0.BackgroundImageFragmentEditableElementValue;
import com.liferay.headless.admin.site.client.dto.v1_0.DirectFragmentImageValue;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentEditableElement;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentEditableElementValue;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentEditableElementValueFragmentLink;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentImage;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentImageValue;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentImageViewport;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentInlineValue;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentLink;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentLinkTextValue;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentMappedValue;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentMappedValueItemContextReference;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentMappedValueItemExternalReference;
import com.liferay.headless.admin.site.client.dto.v1_0.FragmentMappedValueItemReference;
import com.liferay.headless.admin.site.client.dto.v1_0.HTMLFragmentEditableElementValue;
import com.liferay.headless.admin.site.client.dto.v1_0.HTMLFragmentInlineValue;
import com.liferay.headless.admin.site.client.dto.v1_0.HTMLFragmentMappedValue;
import com.liferay.headless.admin.site.client.dto.v1_0.HTMLFragmentValue;
import com.liferay.headless.admin.site.client.dto.v1_0.ImageFragmentEditableElementValue;
import com.liferay.headless.admin.site.client.dto.v1_0.ImageValue;
import com.liferay.headless.admin.site.client.dto.v1_0.ItemExternalReference;
import com.liferay.headless.admin.site.client.dto.v1_0.ItemImageValue;
import com.liferay.headless.admin.site.client.dto.v1_0.LinkFragmentEditableElementValue;
import com.liferay.headless.admin.site.client.dto.v1_0.MappedFragmentImageValue;
import com.liferay.headless.admin.site.client.dto.v1_0.Mapping;
import com.liferay.headless.admin.site.client.dto.v1_0.TextFragmentEditableElementValue;
import com.liferay.headless.admin.site.client.dto.v1_0.TextFragmentInlineValue;
import com.liferay.headless.admin.site.client.dto.v1_0.TextFragmentMappedValue;
import com.liferay.headless.admin.site.client.dto.v1_0.TextFragmentValue;
import com.liferay.headless.admin.site.client.dto.v1_0.URLImageValue;
import com.liferay.headless.admin.site.client.scope.Scope;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.TreeSet;

/**
 * @author Rubén Pulido
 */
public class FragmentEditableElementTestUtil {

	public static FragmentEditableElement
		getBackgroundImageFragmentEditableElement(
			FragmentImageValue fragmentImageValue, String id) {

		FragmentEditableElement fragmentEditableElement =
			new FragmentEditableElement();

		BackgroundImageFragmentEditableElementValue
			backgroundImageFragmentEditableElementValue =
				new BackgroundImageFragmentEditableElementValue();

		backgroundImageFragmentEditableElementValue.
			setBackgroundFragmentImageValue(() -> fragmentImageValue);
		backgroundImageFragmentEditableElementValue.setType(
			() -> FragmentEditableElementValue.Type.BACKGROUND_IMAGE);

		fragmentEditableElement.setFragmentEditableElementValue(
			() -> backgroundImageFragmentEditableElementValue);

		fragmentEditableElement.setId(() -> id);

		return fragmentEditableElement;
	}

	public static FragmentImageValue getDirectFragmentImageValue(
		ItemExternalReference itemExternalReference, String url) {

		DirectFragmentImageValue directFragmentImageValue =
			new DirectFragmentImageValue();

		directFragmentImageValue.setType(() -> FragmentImageValue.Type.DIRECT);
		directFragmentImageValue.setValue_i18n(
			HashMapBuilder.put(
				LocaleUtil.toBCP47LanguageId(LocaleUtil.getDefault()),
				() -> {
					if (itemExternalReference == null) {
						URLImageValue urlImageValue = new URLImageValue();

						urlImageValue.setType(() -> ImageValue.Type.URL);
						urlImageValue.setUrl(() -> url);

						return urlImageValue;
					}

					ItemImageValue itemImageValue = new ItemImageValue();

					itemImageValue.setItemExternalReference(
						() -> itemExternalReference);
					itemImageValue.setType(() -> ImageValue.Type.ITEM);

					return itemImageValue;
				}
			).build());

		return directFragmentImageValue;
	}

	public static FragmentImage getFragmentImage(
		Map<String, String> descriptionMap,
		FragmentImageValue fragmentImageValue, Boolean lazyLoading,
		Map<String, String> resolutionMap) {

		FragmentImage fragmentImage = new FragmentImage();

		fragmentImage.setDescription_i18n(() -> descriptionMap);
		fragmentImage.setFragmentImageValue(() -> fragmentImageValue);
		fragmentImage.setFragmentImageViewports(
			() -> {
				FragmentImageViewport[] fragmentImageViewports =
					TransformUtil.transformToArray(
						new TreeSet<>(resolutionMap.keySet()),
						key -> {
							FragmentImageViewport.Id id =
								FragmentImageViewport.Id.create(key);

							if (id == null) {
								return null;
							}

							FragmentImageViewport fragmentImageViewport =
								new FragmentImageViewport();

							fragmentImageViewport.setId(() -> id);
							fragmentImageViewport.setResolution(
								() -> resolutionMap.get(key));

							return fragmentImageViewport;
						},
						FragmentImageViewport.class);

				if (ArrayUtil.isEmpty(fragmentImageViewports)) {
					return null;
				}

				return fragmentImageViewports;
			});
		fragmentImage.setLazyLoading(() -> lazyLoading);

		return fragmentImage;
	}

	public static FragmentEditableElement getHTMLFragmentEditableElement(
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		FragmentEditableElementValue.Type fragmentEditableElementValueType,
		FragmentMappedValueItemReference.Type
			fragmentMappedValueItemReferenceType,
		HTMLFragmentValue.Type htmlFragmentValueType, String id) {

		FragmentEditableElement fragmentEditableElement =
			new FragmentEditableElement();

		HTMLFragmentEditableElementValue htmlFragmentEditableElementValue =
			new HTMLFragmentEditableElementValue();

		htmlFragmentEditableElementValue.setHtmlFragmentValue(
			() -> _getHTMLFragmentValue(
				contextSource, fragmentMappedValueItemReferenceType,
				htmlFragmentValueType));
		htmlFragmentEditableElementValue.setType(
			() -> fragmentEditableElementValueType);

		fragmentEditableElement.setFragmentEditableElementValue(
			() -> htmlFragmentEditableElementValue);

		fragmentEditableElement.setId(() -> id);

		return fragmentEditableElement;
	}

	public static FragmentEditableElement getImageFragmentEditableElement(
		FragmentImage fragmentImage, String id) {

		FragmentEditableElement fragmentEditableElement =
			new FragmentEditableElement();

		ImageFragmentEditableElementValue imageFragmentEditableElementValue =
			new ImageFragmentEditableElementValue();

		imageFragmentEditableElementValue.setFragmentImage(() -> fragmentImage);
		imageFragmentEditableElementValue.setType(
			() -> FragmentEditableElementValue.Type.IMAGE);

		fragmentEditableElement.setFragmentEditableElementValue(
			() -> imageFragmentEditableElementValue);

		fragmentEditableElement.setId(() -> id);

		return fragmentEditableElement;
	}

	public static FragmentEditableElement getLinkFragmentEditableElement(
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		FragmentLink fragmentLink,
		FragmentMappedValueItemReference.Type
			fragmentMappedValueItemReferenceType,
		String id, FragmentEditableElementValueFragmentLink.Prefix prefix,
		TextFragmentValue.Type textFragmentValueType) {

		FragmentEditableElement fragmentEditableElement =
			new FragmentEditableElement();

		LinkFragmentEditableElementValue linkFragmentEditableElementValue =
			new LinkFragmentEditableElementValue();

		linkFragmentEditableElementValue.setFragmentLinkTextValue(
			() -> _getFragmentLinkTextValue(
				prefix, fragmentLink, contextSource,
				fragmentMappedValueItemReferenceType, textFragmentValueType));
		linkFragmentEditableElementValue.setType(
			() -> FragmentEditableElementValue.Type.LINK);

		fragmentEditableElement.setFragmentEditableElementValue(
			() -> linkFragmentEditableElementValue);

		fragmentEditableElement.setId(() -> id);

		return fragmentEditableElement;
	}

	public static FragmentImageValue getMappedFragmentImageValue(
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		String fieldKey,
		FragmentMappedValueItemReference.Type
			fragmentMappedValueItemReferenceType) {

		MappedFragmentImageValue mappedFragmentImageValue =
			new MappedFragmentImageValue();

		mappedFragmentImageValue.setFragmentMappedValue(
			() -> _getFragmentMappedValue(
				contextSource, fieldKey, fragmentMappedValueItemReferenceType));
		mappedFragmentImageValue.setType(() -> FragmentImageValue.Type.MAPPED);

		return mappedFragmentImageValue;
	}

	public static FragmentImageValue getMappedFragmentImageValue(
		String fieldKey, String className, String externalReferenceCode,
		String scopeExternalReferenceCode) {

		MappedFragmentImageValue mappedFragmentImageValue =
			new MappedFragmentImageValue();

		mappedFragmentImageValue.setFragmentMappedValue(
			() -> _getFragmentMappedValue(
				className, externalReferenceCode, fieldKey,
				scopeExternalReferenceCode));
		mappedFragmentImageValue.setType(() -> FragmentImageValue.Type.MAPPED);

		return mappedFragmentImageValue;
	}

	public static FragmentEditableElement getTextFragmentEditableElement(
		FragmentEditableElementValueFragmentLink.Prefix prefix,
		FragmentLink fragmentLink,
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		FragmentMappedValueItemReference.Type
			fragmentMappedValueItemReferenceType,
		TextFragmentValue.Type textFragmentValueType) {

		FragmentEditableElement fragmentEditableElement =
			new FragmentEditableElement();

		TextFragmentEditableElementValue textFragmentEditableElementValue =
			new TextFragmentEditableElementValue();

		textFragmentEditableElementValue.setFragmentLinkTextValue(
			() -> _getFragmentLinkTextValue(
				prefix, fragmentLink, contextSource,
				fragmentMappedValueItemReferenceType, textFragmentValueType));
		textFragmentEditableElementValue.setType(
			() -> FragmentEditableElementValue.Type.TEXT);

		fragmentEditableElement.setFragmentEditableElementValue(
			() -> textFragmentEditableElementValue);

		fragmentEditableElement.setId(() -> "element-text");

		return fragmentEditableElement;
	}

	private static FragmentEditableElementValueFragmentLink
		_getFragmentEditableElementValueFragmentLink(
			FragmentEditableElementValueFragmentLink.Prefix prefix,
			FragmentLink fragmentLink) {

		if (fragmentLink == null) {
			return null;
		}

		FragmentEditableElementValueFragmentLink
			fragmentEditableElementValueFragmentLink =
				new FragmentEditableElementValueFragmentLink();

		fragmentEditableElementValueFragmentLink.setFragmentLink(
			() -> fragmentLink);
		fragmentEditableElementValueFragmentLink.setPrefix(() -> prefix);

		return fragmentEditableElementValueFragmentLink;
	}

	private static FragmentInlineValue _getFragmentInlineValue() {
		FragmentInlineValue fragmentInlineValue = new FragmentInlineValue();

		fragmentInlineValue.setValue_i18n(
			() -> HashMapBuilder.put(
				"en-US", RandomTestUtil.randomString()
			).put(
				"es-ES", RandomTestUtil.randomString()
			).build());

		return fragmentInlineValue;
	}

	private static FragmentLinkTextValue _getFragmentLinkTextValue(
		FragmentEditableElementValueFragmentLink.Prefix prefix,
		FragmentLink fragmentLink,
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		FragmentMappedValueItemReference.Type
			fragmentMappedValueItemReferenceType,
		TextFragmentValue.Type textFragmentValueType) {

		return new FragmentLinkTextValue() {
			{
				setFragmentEditableElementValueFragmentLink(
					() -> _getFragmentEditableElementValueFragmentLink(
						prefix, fragmentLink));
				setTextFragmentValue(
					() -> _getTextFragmentValue(
						contextSource, fragmentMappedValueItemReferenceType,
						textFragmentValueType));
			}
		};
	}

	private static FragmentMappedValue _getFragmentMappedValue(
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		String fieldKey, FragmentMappedValueItemReference.Type type) {

		return _getFragmentMappedValue(
			fieldKey,
			_getFragmentMappedValueItemReference(contextSource, type));
	}

	private static FragmentMappedValue _getFragmentMappedValue(
		String fieldKey,
		FragmentMappedValueItemReference fragmentMappedValueItemReference) {

		FragmentMappedValue fragmentMappedValue = new FragmentMappedValue();

		Mapping mapping = new Mapping();

		mapping.setFieldKey(() -> fieldKey);
		mapping.setItemReference(() -> fragmentMappedValueItemReference);

		fragmentMappedValue.setMapping(() -> mapping);

		return fragmentMappedValue;
	}

	private static FragmentMappedValue _getFragmentMappedValue(
		String className, String externalReferenceCode, String fieldKey,
		String scopeExternalReferenceCode) {

		return _getFragmentMappedValue(
			fieldKey,
			_getFragmentMappedValueItemExternalReference(
				className, externalReferenceCode, scopeExternalReferenceCode));
	}

	private static FragmentMappedValueItemContextReference
		_getFragmentMappedValueItemContextReference(
			FragmentMappedValueItemContextReference.ContextSource
				contextSource) {

		FragmentMappedValueItemContextReference
			fragmentMappedValueItemContextReference =
				new FragmentMappedValueItemContextReference();

		fragmentMappedValueItemContextReference.setContextSource(contextSource);
		fragmentMappedValueItemContextReference.setType(
			FragmentMappedValueItemReference.Type.CONTEXT_REFERENCE);

		return fragmentMappedValueItemContextReference;
	}

	private static FragmentMappedValueItemExternalReference
		_getFragmentMappedValueItemExternalReference(
			String className, String externalReferenceCode,
			String scopeExternalReferenceCode) {

		FragmentMappedValueItemExternalReference
			fragmentMappedValueItemExternalReference =
				new FragmentMappedValueItemExternalReference() {
					{
						setType(Type.ITEM_EXTERNAL_REFERENCE);
					}
				};

		fragmentMappedValueItemExternalReference.setClassName(() -> className);
		fragmentMappedValueItemExternalReference.setExternalReferenceCode(
			() -> externalReferenceCode);
		fragmentMappedValueItemExternalReference.setScope(
			() -> {
				if (Validator.isNull(scopeExternalReferenceCode)) {
					return null;
				}

				Scope scope = new Scope();

				scope.setExternalReferenceCode(
					() -> scopeExternalReferenceCode);
				scope.setType(() -> Scope.Type.SITE);

				return scope;
			});

		return fragmentMappedValueItemExternalReference;
	}

	private static FragmentMappedValueItemReference
		_getFragmentMappedValueItemReference(
			FragmentMappedValueItemContextReference.ContextSource contextSource,
			FragmentMappedValueItemReference.Type type) {

		if (type == FragmentMappedValueItemReference.Type.CONTEXT_REFERENCE) {
			return _getFragmentMappedValueItemContextReference(contextSource);
		}

		if (type ==
				FragmentMappedValueItemReference.Type.ITEM_EXTERNAL_REFERENCE) {

			return _getFragmentMappedValueItemExternalReference(
				FileEntry.class.getName(), RandomTestUtil.randomString(), null);
		}

		return null;
	}

	private static HTMLFragmentInlineValue _getHTMLFragmentInlineValue() {
		return new HTMLFragmentInlineValue() {
			{
				setFragmentInlineValue(() -> _getFragmentInlineValue());
				setType(Type.INLINE);
			}
		};
	}

	private static HTMLFragmentMappedValue _getHTMLFragmentMappedValue(
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		FragmentMappedValueItemReference.Type
			fragmentMappedValueItemReferenceType) {

		return new HTMLFragmentMappedValue() {
			{
				setFragmentMappedValue(
					() -> _getFragmentMappedValue(
						contextSource, "field-key",
						fragmentMappedValueItemReferenceType));
				setType(Type.MAPPED);
			}
		};
	}

	private static HTMLFragmentValue _getHTMLFragmentValue(
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		FragmentMappedValueItemReference.Type
			fragmentMappedValueItemReferenceType,
		HTMLFragmentValue.Type htmlFragmentValueType) {

		if (htmlFragmentValueType == HTMLFragmentValue.Type.INLINE) {
			return _getHTMLFragmentInlineValue();
		}

		if (htmlFragmentValueType == HTMLFragmentValue.Type.MAPPED) {
			return _getHTMLFragmentMappedValue(
				contextSource, fragmentMappedValueItemReferenceType);
		}

		return null;
	}

	private static TextFragmentInlineValue _getTextFragmentInlineValue() {
		return new TextFragmentInlineValue() {
			{
				setFragmentInlineValue(() -> _getFragmentInlineValue());
				setType(Type.INLINE);
			}
		};
	}

	private static TextFragmentMappedValue _getTextFragmentMappedValue(
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		FragmentMappedValueItemReference.Type
			fragmentMappedValueItemReferenceType) {

		return new TextFragmentMappedValue() {
			{
				setFragmentMappedValue(
					() -> _getFragmentMappedValue(
						contextSource, "field-key",
						fragmentMappedValueItemReferenceType));
				setType(Type.MAPPED);
			}
		};
	}

	private static TextFragmentValue _getTextFragmentValue(
		FragmentMappedValueItemContextReference.ContextSource contextSource,
		FragmentMappedValueItemReference.Type
			fragmentMappedValueItemReferenceType,
		TextFragmentValue.Type textFragmentValueType) {

		if (textFragmentValueType == TextFragmentValue.Type.INLINE) {
			return _getTextFragmentInlineValue();
		}

		if (textFragmentValueType == TextFragmentValue.Type.MAPPED) {
			return _getTextFragmentMappedValue(
				contextSource, fragmentMappedValueItemReferenceType);
		}

		return null;
	}

}