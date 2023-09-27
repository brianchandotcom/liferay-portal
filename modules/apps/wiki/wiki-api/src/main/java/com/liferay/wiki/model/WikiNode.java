/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the WikiNode service. Represents a row in the &quot;WikiNode&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see WikiNodeModel
 * @generated
 */
@ImplementationClassName("com.liferay.wiki.model.impl.WikiNodeImpl")
@ProviderType
public interface WikiNode extends PersistedModel, WikiNodeModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.wiki.model.impl.WikiNodeImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<WikiNode, Long> NODE_ID_ACCESSOR =
		new Accessor<WikiNode, Long>() {

			@Override
			public Long get(WikiNode wikiNode) {
				return wikiNode.getNodeId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<WikiNode> getTypeClass() {
				return WikiNode.class;
			}

		};

	public com.liferay.portal.kernel.repository.model.Folder
			addAttachmentsFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getAttachmentsFolderId();

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getDeletedAttachmentsFiles()
		throws com.liferay.portal.kernel.exception.PortalException;

}