/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.invitation.invite.members.internal.upgrade.registry;

import com.liferay.invitation.invite.members.internal.upgrade.v1_0_0.NamespaceUpgradeProcess;
import com.liferay.invitation.invite.members.internal.upgrade.v1_0_0.UpgradePortletId;
import com.liferay.invitation.invite.members.internal.upgrade.v2_0_0.util.MemberRequestTable;
import com.liferay.portal.kernel.upgrade.BaseSQLServerDatetimeUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(service = UpgradeStepRegistrator.class)
public class InviteMembersServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.1", "1.0.1", new NamespaceUpgradeProcess(),
			new UpgradePortletId());

		// See LPS-65946

		registry.register(
			"1.0.0", "1.0.1", new NamespaceUpgradeProcess(),
			new UpgradePortletId());

		registry.register(
			"1.0.1", "2.0.0",
			new BaseSQLServerDatetimeUpgradeProcess(
				new Class<?>[] {MemberRequestTable.class}));
	}

}