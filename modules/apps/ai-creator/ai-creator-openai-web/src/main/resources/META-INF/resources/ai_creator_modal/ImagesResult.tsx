/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayCard from '@clayui/card';
import React from 'react';

interface Props {
	imagesURL: string[];
}

export function ImagesResult({imagesURL}: Props) {
	return (
        <>
			<p>
				{Liferay.Language.get('image-results')}
			</p>

            <ul className="card-page card-page-equal-height">

			{imagesURL.map((imageURL, index) => (
                <li className="card-page-item mr-2"  key={index}>
                    <ClayCard displayType="image" selectable>
                        <img src={imageURL} style={{ width: "200px" }}/>
                    </ClayCard>
                </li>
            ))}
            </ul>
        </>
	);
}
