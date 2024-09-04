/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Text} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import React, {useState} from 'react';

interface ITitleProps {
	description?: string;
	section?: boolean;
	value: string;
}

const Title: React.FC<ITitleProps> = ({description, section, value}) => {
	const [showDescription, setShowDescription] = useState(false);

	return (
		<>
			<Text color="secondary" size={3} weight="semi-bold">
				<span className="text-uppercase">{value}</span>
			</Text>

			{description && (
				<ClayPopover
					alignPosition="top"
					header={value}
					show={showDescription}
					trigger={
						<ClayButton
							aria-labelledby={value}
							displayType="unstyled"
							onMouseOut={() => setShowDescription(false)}
							onMouseOver={() => setShowDescription(true)}
						>
							<Text color="secondary">
								<ClayIcon symbol="question-circle" />
							</Text>
						</ClayButton>
					}
				>
					{description}
				</ClayPopover>
			)}

			{section && <hr className="mt-0" />}
		</>
	);
};

export default Title;
