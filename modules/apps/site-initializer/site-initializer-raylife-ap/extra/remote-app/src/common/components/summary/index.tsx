/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

type SummaryProps = {
	dataSummary: {
		data?: string | number;
		greenColor?: boolean;
		icon?: boolean;
		key: string;
		redirectTo?: string;
		text: string;
		type?: string;
	}[];
};

const summaryOnClickRules = (
	elementRedirectLink: string | undefined,
	elementKey: string
) => {
	if (elementKey === 'entryID') {
		return window.open(elementRedirectLink);
	}

	if (elementKey === 'email') {
		window.location.href = `mailto:${elementRedirectLink}`;
	}
};

const Summary: React.FC<SummaryProps> = ({dataSummary}) => {
	return (
		<div className="bg-neutral-0 h-100 ray-summary-container rounded w-100">
			<div className="pt-3 px-5 summary-title">
				<h5 className="m-0">Summary</h5>
			</div>

			<hr />

			<div className="d-flex flex-column pb-5 px-5 summary-content">
				<div className="d-flex flex-column mb-3">
					{dataSummary &&
						dataSummary.map((element, index: number) => {
							return (
								<div key={index}>
									<div className="mb-2 text-neutral-7">
										{element.text}
									</div>

									{element.data ? (
										<div
											className={classNames('mb-3', {
												'cursor-pointer text-primary':
													element.type === 'link',
												'text-success':
													element.greenColor &&
													index === 0,
											})}
											onClick={() => {
												element.type === 'link' &&
													summaryOnClickRules(
														element.redirectTo,
														element.key
													);
											}}
										>
											{element.icon ? (
												<>
													{element.data}{' '}
													<ClayIcon symbol="shortcut" />
												</>
											) : (
												element.data
											)}
										</div>
									) : (
										<i className="mb-3">No data</i>
									)}
								</div>
							);
						})}
				</div>
			</div>
		</div>
	);
};

export default Summary;
