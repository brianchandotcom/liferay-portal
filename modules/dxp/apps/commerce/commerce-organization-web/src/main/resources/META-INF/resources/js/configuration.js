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

import {render} from '@liferay/frontend-js-react-web';
import Autocomplete from 'commerce-frontend-js/components/autocomplete/Autocomplete'

export default function({
    namespace,
    rootOrganizationId,
    rootOrganizationName,
    wrapperId
}) {
    render(
        Autocomplete,
        {
            apiUrl: "/o/headless-admin-user/v1.0/organizations?flatten=true",
            initialLabel: rootOrganizationName,
            initialValue: rootOrganizationId,
            inputName: `${namespace}preferences--rootOrganizationId--`,
            itemsKey: "id",
            itemsLabel: "name",
            required: true
        },
        document.getElementById(wrapperId)
    )
}