/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {ClayIconSpriteContext} from '@clayui/icon';
import {Root, createRoot} from 'react-dom/client';
import {SWRConfig} from 'swr';

import {WebDAV} from './common/context/WebDAV';
import {AppRouteType} from './common/enums/appRouteType';
import {PartnerOpportunitiesColumnKey} from './common/enums/partnerOpportunitiesColumnKey';
import getIconSpriteMap from './common/utils/getIconSpriteMap';
import handleError from './common/utils/handleError';
import DealRegistrationForm from './routes/DealRegistrationForm';
import DealRegistrationList from './routes/DealRegistrationList';
import MDFClaimForm from './routes/MDFClaimForm';
import MDFClaimList from './routes/MDFClaimList';
import MDFRequestForm from './routes/MDFRequestForm';
import MDFRequestList from './routes/MDFRequestList';
import PartnerOpportunitiesList from './routes/PartnerOpportunitiesList';
import getOpportunityDates from './routes/PartnerOpportunitiesList/utils/getOpportunityDates';
import getRenewalsDates from './routes/PartnerOpportunitiesList/utils/getRenewalsDates';

interface IProps {
	liferayWebDAV: string;
	route: AppRouteType;
}

type AppRouteComponent = {
	[key in AppRouteType]?: JSX.Element;
};

const appRoutes: AppRouteComponent = {
	[AppRouteType.MDF_REQUEST_FORM]: <MDFRequestForm />,
	[AppRouteType.MDF_REQUEST_LIST]: <MDFRequestList />,
	[AppRouteType.MDF_CLAIM_FORM]: <MDFClaimForm />,
	[AppRouteType.MDF_CLAIM_LIST]: <MDFClaimList />,
	[AppRouteType.DEAL_REGISTRATION_FORM]: <DealRegistrationForm />,
	[AppRouteType.DEAL_REGISTRATION_LIST]: (
		<DealRegistrationList sort="dateCreated:desc" />
	),
	[AppRouteType.PARTNER_OPPORTUNITIES_LIST]: (
		<PartnerOpportunitiesList
			columnsDates={[
				{
					columnKey: PartnerOpportunitiesColumnKey.START_DATE,
					label: 'Start Date',
				},
				{
					columnKey: PartnerOpportunitiesColumnKey.END_DATE,
					label: 'End Date',
				},
			]}
			getDates={(item) =>
				getOpportunityDates(
					item.projectSubscriptionStartDate,
					item.projectSubscriptionStartDate
				)
			}
			getFilteredItems={(items) => items}
			name="Partner Opportunities"
			sort="dateCreated:desc"
		/>
	),
	[AppRouteType.RENEWALS_OPPORTUNITIES_LIST]: (
		<PartnerOpportunitiesList
			columnsDates={[
				{
					columnKey: PartnerOpportunitiesColumnKey.CLOSE_DATE,
					label: 'Close Date',
				},
			]}
			getDates={(item) => getRenewalsDates(item.closeDate)}
			getFilteredItems={(items) =>
				items.filter((item) => item.STAGE !== 'Closed Lost')
			}
			name="Renewal Opportunities"
			sort="closeDate:asc"
		/>
	),
};

const PartnerPortalApp = ({liferayWebDAV, route}: IProps) => {
	return (
		<SWRConfig
			value={{
				onError: (error) => handleError(error),
				revalidateOnFocus: false,
				revalidateOnReconnect: false,
				shouldRetryOnError: false,
			}}
		>
			<WebDAV value={liferayWebDAV}>
				<ClayIconSpriteContext.Provider value={getIconSpriteMap()}>
					{appRoutes[route]}
				</ClayIconSpriteContext.Provider>
			</WebDAV>
		</SWRConfig>
	);
};

class PartnerPortalRemoteAppComponent extends HTMLElement {
	private root: Root | undefined;

	connectedCallback() {
		if (!this.root) {
			this.root = createRoot(this);

			this.root.render(
				<PartnerPortalApp
					liferayWebDAV={
						super.getAttribute('liferaywebdavurl') as string
					}
					route={super.getAttribute('route') as AppRouteType}
				/>
			);
		}
	}
}

const ELEMENT_NAME = 'liferay-remote-app-partner-portal';

if (!customElements.get(ELEMENT_NAME)) {
	customElements.define(ELEMENT_NAME, PartnerPortalRemoteAppComponent);
}
