import {useState} from 'react';
import {LiferayTheme} from '../services/liferay';
import {getAccountFlagByFilter} from '../services/liferay/graphql/account-flags';
import {PARAMS_KEYS} from '../services/liferay/search-params';
import useGraphQL from './useGraphQL';

const liferaySiteName = LiferayTheme.getLiferaySiteName();
const PROJECT_PAGE_KEY = 'projects';

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
	userAccount,
	accountFlags,
	externalReferenceCode
) => {
	return {
		location: `${liferaySiteName}/onboarding?${PARAMS_KEYS.PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE}=${externalReferenceCode}`,
		validate:
			!accountFlags.length &&
			userAccount.roleBriefs.find(
				({name}) => name === 'Account Administrator'
			) &&
			validateExternalReferenceCode(
				userAccount.accountBriefs,
				externalReferenceCode
			),
	};
};

const overviewPageGuard = (
	userAccount,
	_accountFlags,
	externalReferenceCode
) => {
	const isValidExternalReferenceCode = validateExternalReferenceCode(
		userAccount.accountBriefs,
		externalReferenceCode
	);
	const validation =
		isValidExternalReferenceCode || userAccount.accountBriefs.length === 1;

	const getExternalReferenceCode = () => {
		if (isValidExternalReferenceCode) {
			return externalReferenceCode;
		}
		else if (userAccount.accountBriefs.length === 1) {
			return userAccount.accountBriefs[0].externalReferenceCode;
		}
	};

	return {
		location: `${liferaySiteName}/overview?${
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
	const {data, isLoading: isLoadingGraphQL} = useGraphQL([
		getAccountFlagByFilter({
			accountKey: externalReferenceCode,
			name: 'onboarding',
			userUuid: userAccount.externalReferenceCode,
			value: 1,
		}),
	]);

	if (!isLoadingGraphQL) {
		setLoading(isLoadingGraphQL);

		if (
			!validateExternalReferenceCode(
				userAccount.accountBriefs,
				externalReferenceCode
			) ||
			!guard(userAccount, data.accountFlags, externalReferenceCode)
				.validate
		) {
			const {location, validate: alternativeValidate} = alternativeGuard(
				userAccount,
				data.accountFlags,
				externalReferenceCode
			);

			window.location.href = alternativeValidate
				? location
				: `${liferaySiteName}/${PROJECT_PAGE_KEY}`;
		}
	}

	return {
		isLoading,
	};
};

export {usePageGuard, onboardingPageGuard, overviewPageGuard};
