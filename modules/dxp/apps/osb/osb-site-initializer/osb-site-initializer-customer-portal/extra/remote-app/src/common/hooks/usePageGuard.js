import { useState } from "react";
import { LiferayTheme } from "../services/liferay";
import { getAccountFlagByFilter } from "../services/liferay/graphql/account-flags";
import { getUserAccountById } from "../services/liferay/graphql/user-accounts";
import useGraphQL from "./useGraphql";

const liferaySiteName = LiferayTheme.getLiferaySiteName();

const onboardingPageRedirection = (userAccount, accountFlags) => {
    return {
        pageKey: "onboarding",
        validate: !accountFlags.length && userAccount.roleBriefs.find(({ name }) => name === 'Account Administrator'),
    };
}

const overviewPageRedirection = (userAccount) => {
    return {
        pageKey: "project-overview",
        validate: userAccount.accountBriefs.length === 1,
    };
}

const projectsPageRedirection = (userAccount) => {
    return {
        pageKey: "projects-list",
        validate: userAccount.accountBriefs.length !== 1,
    };
}

// eslint-disable-next-line no-unused-vars
const usePageGuard = (externalReferenceCode, pageRedirect, otherRedirections) => {
    const [isLoading, setLoading] = useState(true);
    const { data, isLoading: isLoadingGraphQL } = useGraphQL([
        getUserAccountById(LiferayTheme.getUserId()),
        getAccountFlagByFilter({
            accountKey: externalReferenceCode,
            name: "onboarding",
            userUuid: LiferayTheme.getUserId(),
            value: 1,
        })
    ]);

    if (!isLoadingGraphQL) {
        setLoading(isLoadingGraphQL);

        if (!externalReferenceCode || !pageRedirect(data.userAccount, data.accountFlags).validate) {
            otherRedirections.forEach((redirection) => {
                const { pageKey, validate } = redirection(data.userAccount, data.accountFlags);

                if (validate) {
                    window.location.href = `${liferaySiteName}/${pageKey}`;
                }
            });
        }
    }

    return {
        isLoading
    }
}

export { usePageGuard, onboardingPageRedirection, overviewPageRedirection, projectsPageRedirection };