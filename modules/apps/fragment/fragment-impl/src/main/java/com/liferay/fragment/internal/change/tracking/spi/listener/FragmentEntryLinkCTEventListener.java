/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.change.tracking.spi.listener;

import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.spi.exception.CTEventException;
import com.liferay.change.tracking.spi.listener.CTEventListener;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(service = CTEventListener.class)
public class FragmentEntryLinkCTEventListener implements CTEventListener {

	@Override
	public void onAfterPublish(long ctCollectionId) throws CTEventException {
		for (CTEntry ctEntry :
				_ctEntryLocalService.getCTEntries(
					ctCollectionId,
					_portal.getClassNameId(
						FragmentEntryLink.class.getName()))) {

			FragmentEntryLink fragmentEntryLink =
				_fragmentEntryLinkLocalService.fetchFragmentEntryLink(
					ctEntry.getModelClassPK());

			if (fragmentEntryLink == null) {
				continue;
			}

			fragmentEntryLink.setHtml(
				_removePreviewCTCollectionId(
					ctCollectionId, fragmentEntryLink.getHtml()));
			fragmentEntryLink.setEditableValues(
				_removePreviewCTCollectionId(
					ctCollectionId, fragmentEntryLink.getEditableValues()));

			_fragmentEntryLinkLocalService.updateFragmentEntryLink(
				fragmentEntryLink);
		}
	}

	private String _removePreviewCTCollectionId(
		long ctCollectionId, String string) {

		String substring = "previewCTCollectionId=" + ctCollectionId;

		while (string.contains(substring)) {
			int index = string.indexOf(substring);

			if (((index + substring.length()) < string.length()) &&
				(string.charAt(index + substring.length()) == '&')) {

				string = StringUtil.removeSubstring(
					string,
					string.substring(index, index + substring.length() + 1));
			}
			else if (((index - 1) >= 0) &&
					 ((string.charAt(index - 1) == '?') ||
					  (string.charAt(index - 1) == '&'))) {

				string = StringUtil.removeSubstring(
					string,
					string.substring(index - 1, index + substring.length()));
			}
			else {
				string = StringUtil.removeSubstring(
					string,
					string.substring(index, index + substring.length()));
			}
		}

		return string;
	}

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private Portal _portal;

}