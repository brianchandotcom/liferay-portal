/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.change.tracking.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.knowledge.base.model.KBComment;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Vy Bui
 */
@Component(service = CTDisplayRenderer.class)
public class KBCommentCTDisplayRenderer
	extends BaseCTDisplayRenderer<KBComment> {

	@Override
	public Class<KBComment> getModelClass() {
		return KBComment.class;
	}

	@Override
	public String getTitle(Locale locale, KBComment kbComment)
		throws PortalException {

		return StringBundler.concat(
			getTypeName(locale), " ", kbComment.getKbCommentId());
	}

	@Override
	public boolean isHideable(KBComment kbComment) {
		return true;
	}

}