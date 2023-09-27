/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.model.impl;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.knowledge.base.model.KBComment;

/**
 * @author Peter Shin
 * @author Daniel Kocsis
 */
public class KBCommentImpl extends KBCommentBaseImpl {

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(KBComment.class);
	}

}