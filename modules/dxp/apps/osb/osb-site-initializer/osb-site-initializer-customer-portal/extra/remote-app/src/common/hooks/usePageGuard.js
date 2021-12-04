import {useQuery} from '@apollo/client';
import {useEffect, useState} from 'react';
import {LiferayTheme} from '../services/liferay';
import {getAccountRolesAndAccountFlags} from '../services/liferay/graphql/queries';
import {PARAMS_KEYS} from '../services/liferay/search-params';
import {API_BASE_URL} from '../utils';

const liferaySiteName = LiferayTheme.getLiferaySiteName();

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
	return `${API_BASE_URL}${liferaySiteName}`;
};

const getOnboardingLocation = (externalReferenceCode) => {
	return `${API_BASE_URL}${liferaySiteName}/onboarding?${PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE}=${externalReferenceCode}`;
};

const getOverviewLocation = (externalReferenceCode) => {
	return `${API_BASE_URL}${liferaySiteName}/overview?${PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE}=${externalReferenceCode}`;
};

const usePageGuard = (userAccount, externalReferenceCode, pageKey) => {
	const [loading, setLoading] = useState(true);

	const {data} = useQuery(getAccountRolesAndAccountFlags, {
		variables: {
			accountFlagsFilter: `accountKey eq '${externalReferenceCode}' and name eq 'onboarding' and userUuid eq '${userAccount.externalReferenceCode}' and value eq 1`,
			accountId: userAccount.id,
		},
	});

	useEffect(() => {
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
				}
				else {
					setLoading(false);
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
				}
				else {
					setLoading(false);
				}
			}
		}
	}, [data, externalReferenceCode, pageKey, userAccount]);

	return {
		loading,
	};
};

export {usePageGuard};
