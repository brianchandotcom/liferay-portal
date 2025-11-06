/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from "@clayui/layout";
import React from "react";

const QuickActions: React.FC<{action: string}> = ({ action }) => {

    return (
        <ClayLayout.ContentCol className="mr-2">
            <ClayButton
                className="ai-assistant-chat__quick-actions-button pl-2 pr-2"
                displayType="unstyled"
                onClick={() => alert(`${action} clicked`)}
                small
            >
                <ClayIcon
                    className="mr-2"
                    height={12}
                    spritemap={Liferay.Icons.spritemap}
                    symbol="stars"
                    width={12}
                />

                {action}
            </ClayButton>
        </ClayLayout.ContentCol>
    );
}

export default QuickActions;