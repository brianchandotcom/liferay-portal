/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.editor.configuration.internal;

import com.liferay.adaptive.media.image.html.constants.AMImageHTMLConstants;
import com.liferay.adaptive.media.image.item.selector.AMImageFileEntryItemSelectorReturnType;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.file.criterion.FileItemSelectorCriterion;
import com.liferay.item.selector.criteria.image.criterion.ImageItemSelectorCriterion;
import com.liferay.item.selector.criteria.upload.criterion.UploadItemSelectorCriterion;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.item.selector.criterion.JournalItemSelectorCriterion;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor;
import com.liferay.portal.kernel.editor.configuration.EditorConfigContributor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"editor.name=alloyeditor", "editor.name=ckeditor",
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"service.ranking:Integer=101"
	},
	service = EditorConfigContributor.class
)
public class AMJournalEditorConfigContributor
	extends BaseAMEditorConfigContributor {

	@Override
	protected boolean isItemSelectorCriterionOverridable(
		ItemSelectorCriterion itemSelectorCriterion) {

		if (itemSelectorCriterion instanceof FileItemSelectorCriterion ||
			itemSelectorCriterion instanceof ImageItemSelectorCriterion ||
			itemSelectorCriterion instanceof JournalItemSelectorCriterion ||
			itemSelectorCriterion instanceof UploadItemSelectorCriterion) {

			return true;
		}

		return false;
	}

}