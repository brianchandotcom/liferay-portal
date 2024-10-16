/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.counter;

import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.adaptive.media.image.validator.AMImageValidator;
import com.liferay.document.library.configuration.DLFileEntryConfigurationProvider;
import com.liferay.document.library.constants.DLFileEntryConfigurationConstants;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Galluzzi
 */
public abstract class BaseAMImageCounter implements AMImageCounter {

	@Override
	public int countExpectedAMImageEntries(long companyId) {
		long previewableProcessorMaxSize =
			dlFileEntryConfigurationProvider.
				getCompanyPreviewableProcessorMaxSize(companyId);

		if (previewableProcessorMaxSize == 0) {
			return 0;
		}

		AtomicInteger counter = new AtomicInteger(0);

		try {
			forEachFileEntry(companyId, _getCountDLFileEntryConsumer(counter));
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return 0;
		}

		return counter.get();
	}

	protected abstract void forEachFileEntry(
			long companyId, Consumer<DLFileEntry> consumer)
		throws PortalException;

	protected String[] getMimeTypes() {
		return ArrayUtil.filter(
			amImageMimeTypeProvider.getSupportedMimeTypes(),
			amImageValidator::isProcessingSupported);
	}

	@Reference
	protected AMImageMimeTypeProvider amImageMimeTypeProvider;

	@Reference
	protected AMImageValidator amImageValidator;

	@Reference
	protected DLFileEntryConfigurationProvider dlFileEntryConfigurationProvider;

	private Consumer<DLFileEntry> _getCountDLFileEntryConsumer(
		AtomicInteger counter) {

		Map<Long, Long> groupPreviewableProcessorMaxSizeMap =
			dlFileEntryConfigurationProvider.
				getGroupPreviewableProcessorMaxSizeMap();

		return dlFileEntry -> {
			Long previewableProcessorMaxSize =
				groupPreviewableProcessorMaxSizeMap.get(
					dlFileEntry.getGroupId());

			if ((previewableProcessorMaxSize == null) ||
				(previewableProcessorMaxSize ==
					DLFileEntryConfigurationConstants.
						PREVIEWABLE_PROCESSOR_MAX_SIZE_UNLIMITED) ||
				(dlFileEntry.getSize() <= previewableProcessorMaxSize)) {

				counter.incrementAndGet();
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAMImageCounter.class);

}