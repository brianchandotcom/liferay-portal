import {ACTIONS} from './actions.es';

import {
	getCheckedCheckboxes,
	postForm,
} from 'frontend-js-web';

const updateUserEntries = (portletNamespace, url) => {
	const form = document.getElementById(`${portletNamespace}fm`);

	if (form) {
		postForm(form, {
			data: {
				UserEntryIds: getCheckedCheckboxes(
					form,
					`${portletNamespace}allRowIds`
				),
			},
			url,
		});
	}
};

export default function propsTransformer({
    additionalProps: {basePortletURL},
	portletNamespace,
	...otherProps
})  {
    const deactivateUsersEntries = (itemData) => {
		updateUserEntries(
			portletNamespace,
			itemData?.deactivateUserEntriesURL
		);
    };

	return {
		...otherProps,
        onActionButtonClick: (_, {item}) => {
			const data = item?.data;

			const action = data?.action;

            if (action === 'selectUsers') {
				ACTIONS.selectUsers({
					basePortletURL,
					organizationId: data?.organizationId,
					portletNamespace,
				});
			}

			else if (action === "deactivateUserEntries") {
				deactivateUsersEntries(data);
			}
		},
	};
}