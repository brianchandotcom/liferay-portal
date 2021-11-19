import { useContext, useEffect } from "react";
import useGraphQL from "~/common/hooks/useGraphQL";
import { getKoroneikiAccountsByFilter } from "~/common/services/liferay/graphql/koroneiki-accounts";
import { AppContext } from '../../context';
import { actionTypes } from "../../context/reducer";
import { CUSTOM_EVENTS } from "../../utils/constants";

const Overview = () => {
    const [{ project }, dispatch] = useContext(AppContext);

    const { data, isLoading: isLoadingKoroneiki } = useGraphQL([
        getKoroneikiAccountsByFilter({
            accountKeys: [
                project.accountKey
            ]
        })
    ]);

    useEffect(() => {
        if (data) {
            dispatch({
                payload: data.koroneikiAccounts[0],
                type: actionTypes.UPDATE_PROJECT,
            });
        
            window.dispatchEvent(
                new CustomEvent(CUSTOM_EVENTS.PROJECT, {
                    bubbles: true,
                    composed: true,
                    detail: {
                        ...data.koroneikiAccounts[0]
                    }
                })
            );
        }
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [data]);

    if (isLoadingKoroneiki) {
        return <div>Overview Skeleton</div>;
    }

    return <div>Overview Page</div>;
};

export default Overview;