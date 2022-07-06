/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayMultiStepNav from '@clayui/multi-step-nav';
import {ReactNode, useState} from 'react';

import ClayIconProvider from '../../../common/context/ClayIconProvider';

type DriverInfoProps = {
	children: ReactNode;
};

const NewApplication = ({children}: DriverInfoProps) => {
	const [value, setValue] = useState<number>(0);

	const steps = [
		{
			active: value === 0,
			complete: value > 0,
			onClick: () => setValue(0),
			title: 'Contact Info',
		},
		{
			active: value === 1,
			complete: value > 1,
			onClick: () => setValue(1),
			title: 'Vehicle Info',
		},
		{
			active: value === 2,
			complete: value > 2,
			onClick: () => setValue(2),
			title: 'Driver Info',
		},
		{
			active: value === 3,
			complete: value > 3,
			onClick: () => setValue(3),
			title: 'Coverages',
		},
		{
			active: value === 4,
			complete: value > 4,
			onClick: () => setValue(4),
			title: 'Review',
		},
	];

	return (
		<ClayIconProvider>
			<div className="container">
				<div className="border mt-4 sheet sheet-dataset-content">
					<div className="d-flex justify-content-between">
						<h5>New Application</h5>

						<div className="text-neutral-7 text-paragraph-sm">
							<ClayIcon symbol="simple-circle"></ClayIcon>
							No Changes Made
						</div>

						<div>
							<ClayButton displayType={null} small={true}>
								EXIT
							</ClayButton>

							<ClayButton displayType="secondary" small={true}>
								SAVE
							</ClayButton>
						</div>
					</div>

					<hr></hr>
					<>
						<ClayMultiStepNav className="mx-10">
							{steps.map(
								({active, complete, onClick, title}, index) => (
									<ClayMultiStepNav.Item
										active={active}
										complete={complete}
										expand={index + 1 !== steps.length}
										key={index}
									>
										<ClayMultiStepNav.Title>
											{title}
										</ClayMultiStepNav.Title>

										{index + 1 !== steps.length ? (
											<ClayMultiStepNav.Divider />
										) : (
											''
										)}

										<ClayMultiStepNav.Indicator
											complete={complete}
											label={1 + index}
											onClick={onClick}
										/>
									</ClayMultiStepNav.Item>
								)
							)}
						</ClayMultiStepNav>
						<hr className="mb-5"></hr>
					</>

					{children}

					<hr></hr>

					<div className="d-flex justify-content-end mt-4">
						<ClayButton displayType="primary" small={true}>
							NEXT
						</ClayButton>
					</div>
				</div>
			</div>
		</ClayIconProvider>
	);
};

export default NewApplication;
