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

package com.liferay.journal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the JournalArticleImage service. Represents a row in the &quot;JournalArticleImage&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleImageModel
 * @see com.liferay.journal.model.impl.JournalArticleImageImpl
 * @see com.liferay.journal.model.impl.JournalArticleImageModelImpl
 * @generated
 */
@ProviderType
public interface JournalArticleImage extends JournalArticleImageModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.journal.model.impl.JournalArticleImageImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<JournalArticleImage, Long> ARTICLE_IMAGE_ID_ACCESSOR =
		new Accessor<JournalArticleImage, Long>() {
			@Override
			public Long get(JournalArticleImage journalArticleImage) {
				return journalArticleImage.getArticleImageId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<JournalArticleImage> getTypeClass() {
				return JournalArticleImage.class;
			}
		};
}