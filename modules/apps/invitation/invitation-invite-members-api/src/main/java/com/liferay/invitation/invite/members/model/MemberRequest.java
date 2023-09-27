/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.invitation.invite.members.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the MemberRequest service. Represents a row in the &quot;IM_MemberRequest&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see MemberRequestModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.invitation.invite.members.model.impl.MemberRequestImpl"
)
@ProviderType
public interface MemberRequest extends MemberRequestModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.invitation.invite.members.model.impl.MemberRequestImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MemberRequest, Long>
		MEMBER_REQUEST_ID_ACCESSOR = new Accessor<MemberRequest, Long>() {

			@Override
			public Long get(MemberRequest memberRequest) {
				return memberRequest.getMemberRequestId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<MemberRequest> getTypeClass() {
				return MemberRequest.class;
			}

		};

}