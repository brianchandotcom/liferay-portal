import client from '../../apolloClient';
import {LiferayTheme} from '../services/liferay';
import {getAccountRolesAndAccountFlags} from '../services/liferay/graphql/queries';
import {PARAMS_KEYS} from '../services/liferay/search-params';

const {REACT_APP_LIFERAY_API = window.location.origin} = process.env;

const API_BASE_URL = REACT_APP_LIFERAY_API;

export {API_BASE_URL};

const getCurrentEndDate = (currentEndDate) => {
	const date = new Date(currentEndDate);
	const month = date.toLocaleDateString('default', {month: 'short'});
	const day = date.getDate();
	const year = date.getFullYear();

	return `${month} ${day}, ${year}`;
};

const downloadFromBlob = (blob, filename) => {
	const a = document.createElement('a');
	a.href = window.URL.createObjectURL(blob);
	a.download = filename;
	document.body.appendChild(a);
	a.click();
	a.remove();
};

const isValidPage = async (userAccount, externalReferenceCode, pageKey) => {
	const validateExternalReferenceCode = (
		accountBriefs,
		externalReferenceCode
	) => {
		const hasAccountBrief = !!accountBriefs.find(
			(accountBrief) =>
				accountBrief.externalReferenceCode === externalReferenceCode
		);

		return hasAccountBrief;
	};

	const getHomeLocation = () => {
		return `${API_BASE_URL}/${LiferayTheme.getLiferaySiteName()}`;
	};

	const getOnboardingLocation = (externalReferenceCode) => {
		return `${API_BASE_URL}/${LiferayTheme.getLiferaySiteName()}/onboarding?${
			PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE
		}=${externalReferenceCode}`;
	};

	const getOverviewLocation = (externalReferenceCode) => {
		return `${API_BASE_URL}/${LiferayTheme.getLiferaySiteName()}/overview?${
			PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE
		}=${externalReferenceCode}`;
	};

	const {data} = await client.query({
		query: getAccountRolesAndAccountFlags,
		variables: {
			accountFlagsFilter: `accountKey eq '${externalReferenceCode}' and name eq 'onboarding' and userUuid eq '${userAccount.externalReferenceCode}' and value eq 1`,
			accountId: userAccount.id,
		},
	});

	if (data) {
		const isValidExternalReferenceCode = validateExternalReferenceCode(
			userAccount.accountBriefs,
			externalReferenceCode
		);
		const hasAccountFlags = !!data.c?.accountFlags?.items?.length;

		if (pageKey === 'onboarding') {
			const isAccountAdministrator = !!data.accountAccountRoles?.items?.find(
				({name}) => name === 'Account Administrator'
			);

			if (
				!(
					isValidExternalReferenceCode &&
					isAccountAdministrator &&
					!hasAccountFlags
				)
			) {
				if (userAccount.accountBriefs.length === 1) {
					window.location.href = getOverviewLocation(
						isValidExternalReferenceCode
							? externalReferenceCode
							: userAccount.accountBriefs[0]
					);
				}
				else {
					window.location.href = getHomeLocation();
				}

				return false;
			}
			else {
				return true;
			}
		}

		if (pageKey === 'overview') {
			if (!isValidExternalReferenceCode) {
				window.location.href = getHomeLocation();
			}
			else if (!hasAccountFlags) {
				window.location.href = getOnboardingLocation(
					externalReferenceCode
				);

				return false;
			}
			else {
				return true;
			}
		}
	}
};

export {getCurrentEndDate, downloadFromBlob, isValidPage};
