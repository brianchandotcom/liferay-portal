/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.on.demand.user.ticket.generator;

import com.liferay.change.tracking.constants.CTOnDemandUserConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.on.demand.user.ticket.generator.CTOnDemandUserTicketGenerator;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Ticket;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.TicketLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.PwdGenerator;
import com.liferay.portal.security.audit.event.generators.util.AuditMessageBuilder;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = CTOnDemandUserTicketGenerator.class)
public class CTOnDemandUserTicketGeneratorImpl
	implements CTOnDemandUserTicketGenerator {

	@Override
	public Ticket generate(long ctCollectionId) throws PortalException {
		CTCollection ctCollection = _ctCollectionLocalService.getCTCollection(
			ctCollectionId);

		if (!ctCollection.isShareable()) {
			return null;
		}

		List<Ticket> tickets = _ticketLocalService.getTickets(
			ctCollection.getCompanyId(), CTCollection.class.getName(),
			ctCollectionId,
			CTOnDemandUserConstants.TICKET_TYPE_ON_DEMAND_USER_LOGIN);

		if (!tickets.isEmpty()) {
			return tickets.get(0);
		}

		User user = _getCTOnDemandUser(ctCollection);

		AuditMessage auditMessage = AuditMessageBuilder.buildAuditMessage(
			CTOnDemandUserConstants.
				AUDIT_EVENT_TYPE_CT_ON_DEMAND_USER_TICKET_GENERATED,
			CTCollection.class.getName(), ctCollectionId, null);

		auditMessage.setAdditionalInfo(
			JSONUtil.put(
				"companyId", ctCollection.getCompanyId()
			).put(
				"onDemandUserId", user.getUserId()
			));

		_auditRouter.route(auditMessage);

		return _ticketLocalService.addDistinctTicket(
			user.getCompanyId(), CTCollection.class.getName(), ctCollectionId,
			CTOnDemandUserConstants.TICKET_TYPE_ON_DEMAND_USER_LOGIN,
			String.valueOf(user.getUserId()), null, null);
	}

	private User _getCTOnDemandUser(CTCollection ctCollection)
		throws PortalException {

		User user = _userLocalService.fetchUserById(
			ctCollection.getOnDemandUserId());

		if (user != null) {
			return user;
		}

		Company company = _companyLocalService.getCompany(
			ctCollection.getCompanyId());

		String screenName = StringBundler.concat(
			CTOnDemandUserConstants.SCREEN_NAME_PREFIX_CT_ON_DEMAND_USER,
			StringPool.UNDERLINE, ctCollection.getCtCollectionId());

		String password = PwdGenerator.getPassword(20);

		Date date = new Date();

		user = _userLocalService.addUser(
			PrincipalThreadLocal.getUserId(), company.getCompanyId(), false,
			password, password, true, screenName,
			StringBundler.concat(screenName, StringPool.AT, company.getMx()),
			company.getLocale(), "guest", null, "guest", 0, 0, true,
			date.getMonth(), date.getDay(), date.getYear(), null,
			UserConstants.TYPE_ON_DEMAND_USER, null, null, null, null, false,
			new ServiceContext());

		user.setEmailAddressVerified(true);

		user = _userLocalService.updateUser(user);

		ctCollection.setOnDemandUserId(user.getUserId());

		_ctCollectionLocalService.updateCTCollection(ctCollection);

		return user;
	}

	@Reference
	private AuditRouter _auditRouter;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private TicketLocalService _ticketLocalService;

	@Reference
	private UserLocalService _userLocalService;

}