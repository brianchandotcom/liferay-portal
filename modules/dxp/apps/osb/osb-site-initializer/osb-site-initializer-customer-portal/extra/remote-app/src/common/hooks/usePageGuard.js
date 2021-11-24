import {useQuery} from '@apollo/client';
import {useEffect, useState} from 'react';
import {LiferayTheme} from '../services/liferay';
import {pageGuard} from '../services/liferay/graphql/queries';
import {PARAMS_KEYS} from '../services/liferay/search-params';

const liferaySiteName = LiferayTheme.getLiferaySiteName();

const validateExternalReferenceCode = (
	accountBriefs,
	externalReferenceCode
) => {
	const accountBrief = accountBriefs.find(
		(accountBrief) =>
			accountBrief.externalReferenceCode === externalReferenceCode
	);

	return accountBrief;
};

const onboardingPageGuard = (
	accountBriefs,
	externalReferenceCode,
	accountFlags,
	accountAccountRoles
) => {
	return {
		location: `${window.location.origin}${liferaySiteName}/onboarding?${PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE}=${externalReferenceCode}`,
		validate:
			!accountFlags.length &&
			accountAccountRoles.find(
				({name}) => name === 'Account Administrator'
			) &&
			validateExternalReferenceCode(accountBriefs, externalReferenceCode),
	};
};

const overviewPageGuard = (accountBriefs, externalReferenceCode) => {
	const isValidExternalReferenceCode = validateExternalReferenceCode(
		accountBriefs,
		externalReferenceCode
	);
	const validation =
		isValidExternalReferenceCode || accountBriefs.length === 1;

	const getExternalReferenceCode = () => {
		if (isValidExternalReferenceCode) {
			return externalReferenceCode;
		}
		else if (accountBriefs.length === 1) {
			return accountBriefs[0].externalReferenceCode;
		}
	};

	return {
		location: `${window.location.origin}${liferaySiteName}/overview?${
			PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE
		}=${getExternalReferenceCode()}`,
		validate: validation,
	};
};

const usePageGuard = (
	userAccount,
	guard,
	alternativeGuard,
	externalReferenceCode
) => {
	const [isLoading, setLoading] = useState(true);

	const {data} = useQuery(pageGuard, {
		variables: {
			accountFlagsFilter: `accountKey eq '${externalReferenceCode}' and name eq 'onboarding' and userUuid eq '${userAccount.externalReferenceCode}' and value eq 1`,
			accountId: userAccount.id
		},
	});

	useEffect(() => {
		if (data) {
			if (
				!validateExternalReferenceCode(
					userAccount.accountBriefs,
					externalReferenceCode
				) ||
				!guard(
					userAccount.accountBriefs,
					externalReferenceCode,
					data.c?.accountFlags?.items,
					data.accountAccountRoles?.items
				).validate
			) {
				const {
					location,
					validate: alternativeValidate,
				} = alternativeGuard(
					userAccount.accountBriefs,
					externalReferenceCode,
					data.c?.accountFlags?.items,
					data.accountAccountRoles?.items
				);

				if (alternativeValidate) {
					window.location.href = location;
				}
				else {
					window.location.href = `${window.location.origin}${liferaySiteName}`;
				}
			}
			else {
				setLoading(false);
			}
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [data]);

	return {
		isLoading,
	};
};

export {usePageGuard, onboardingPageGuard, overviewPageGuard};
