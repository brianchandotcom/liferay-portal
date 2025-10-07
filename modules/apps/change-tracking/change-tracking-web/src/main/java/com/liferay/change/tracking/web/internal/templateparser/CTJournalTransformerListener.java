/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.templateparser;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.templateparser.BaseTransformerListener;
import com.liferay.portal.kernel.templateparser.TransformerListener;

import java.util.Map;

import net.htmlparser.jericho.Attributes;
import net.htmlparser.jericho.OutputDocument;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "jakarta.portlet.name=" + JournalPortletKeys.JOURNAL,
	service = TransformerListener.class
)
public class CTJournalTransformerListener extends BaseTransformerListener {

	@Override
	public String onOutput(
		String output, String languageId, Map<String, String> tokens) {

		if (!output.contains("videoEmbed=true")) {
			return output;
		}

		Long ctCollectionId = Long.valueOf(tokens.get("ct_collection_id"));

		if (ctCollectionId ==
				CTCollectionThreadLocal.CT_COLLECTION_ID_PRODUCTION) {

			return output;
		}

		Source source = new Source(output);

		StartTag iframeTag = source.getFirstStartTag("iframe");

		Attributes attributes = iframeTag.getAttributes();

		OutputDocument outputDocument = new OutputDocument(source);

		Map<String, String> map = outputDocument.replace(attributes, false);

		map.put(
			"src",
			StringBundler.concat(
				attributes.getValue("src"), "&ctCollectionId=",
				ctCollectionId));

		return outputDocument.toString();
	}

}