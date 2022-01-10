import {API_BASE_URL} from '.';
import client from '../../apolloClient';
import {LiferayTheme} from '../services/liferay';
import {getAccountRolesAndAccountFlags} from '../services/liferay/graphql/queries';
import {PARAMS_KEYS} from '../services/liferay/search-params';
import {ROLES_PERMISSIONS, ROUTES} from './constants';

const {PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE} = PARAMS_KEYS;

const BASE_API = `${API_BASE_URL}/${LiferayTheme.getLiferaySiteName()}`;

const getHomeLocation = () => BASE_API;

const getOnboardingLocation = (externalReferenceCode) =>
	`${BASE_API}/onboarding?${PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE}=${externalReferenceCode}`;

const getOverviewLocation = (externalReferenceCode) => {
	return `${BASE_API}/overview?${PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE}=${externalReferenceCode}`;
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

	const {data} = await client.query({
		query: getAccountRolesAndAccountFlags,
		variables: {
			accountFlagsFilter: `accountKey eq '${externalReferenceCode}' and name eq '${ROUTES.ONBOARDING}' and finished eq true`,
			accountId: userAccount.id,
		},
	});

	if (data) {
		const isValidExternalReferenceCode = validateExternalReferenceCode(
			userAccount.accountBriefs,
			externalReferenceCode
		);
		const hasAccountFlags = !!data.c?.accountFlags?.items?.length;

		if (pageKey === ROUTES.ONBOARDING) {
			const isAccountAdministrator = !!data.accountAccountRoles?.items?.find(
				({name}) => name === ROLES_PERMISSIONS.ACCOUNT_ADMINISTRATOR
			);

			if (
				!(
					isValidExternalReferenceCode &&
					isAccountAdministrator &&
					!hasAccountFlags
				)
			) {
				window.location.href =
					userAccount.accountBriefs.length === 1
						? getOverviewLocation(
								isValidExternalReferenceCode
									? externalReferenceCode
									: userAccount.accountBriefs[0]
						  )
						: getHomeLocation();

				return false;
			}

			return true;
		}

		if (pageKey === ROUTES.OVERVIEW) {
			if (!isValidExternalReferenceCode) {
				window.location.href = getHomeLocation();
			}
			else if (!hasAccountFlags) {
				window.location.href = getOnboardingLocation(
					externalReferenceCode
				);

				return false;
			}

			return true;
		}
	}
};

export {isValidPage};
