/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, { useEffect } from 'react';

interface Props {
    constants: String
}

export default function ViewWorkflowTasks({
    constants
}: Props) {

    useEffect(() => {
        // eslint-disable-next-line no-console
        console.log('constants', constants);
    }, [constants]);

    return (
        <div>
            test
        </div>
    );
}
