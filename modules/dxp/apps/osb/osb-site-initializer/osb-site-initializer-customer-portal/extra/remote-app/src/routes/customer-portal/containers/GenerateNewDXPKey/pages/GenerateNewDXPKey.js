/* eslint-disable no-unused-vars */
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

import {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';
import Button from '../../../../../common/components/Button';
import {Radio} from '../../../../../common/components/Radio';
import Layout from '../../../../../common/containers/setup-forms/Layout';

const GenerateNewDXPKey = () => {
	const [selected, setSelected] = useState();

	const options = [
		{
			label: 'Option 1',
			value: 1,
		},
		{
			label: 'Option 2',
			value: 2,
		},
		{
			label: 'Option 3',
			value: 3,
		},
	];

	return (
		<Layout
			footerProps={{
				footerClass: 'mx-5 mb-6',
				leftButton: <Button displayType={null}>Cancel</Button>,
				middleButton: <Button displayType="primary">Next</Button>,
			}}
			headerProps={{
				headerClass: 'ml-5 mt-4 mb-3',
				helper:
					'Select the subscription and key type you would like to generate.',
				title: 'Generate Activation Key(s)',
			}}
			layoutType="generateKey"
		>
			<div className="px-6">
				<div className="d-flex justify-content-between mb-2">
					<div className="cp-generate-key-select mr-3 w-100">
						<label htmlFor="basicInput">Product</label>

						<div className="position-relative">
							<ClaySelect className="cp-generate-key-select mr-2">
								{options.map((option) => (
									<ClaySelect.Option
										key={option.value}
										label={option.label}
									/>
								))}
							</ClaySelect>

							<ClayIcon
								className="select-icon"
								symbol="caret-bottom"
							/>
						</div>
					</div>

					<div className="cp-generate-key-select ml-3 w-100">
						<label htmlFor="basicInput">Version</label>

						<div className="position-relative">
							<ClaySelect className="cp-generate-key-select mr-2">
								{options.map((option) => (
									<ClaySelect.Option
										key={option.value}
										label={option.label}
									/>
								))}
							</ClaySelect>

							<ClayIcon
								className="select-icon"
								symbol="caret-bottom"
							/>
						</div>
					</div>
				</div>

				<div className="cp-generate-key-select mt-4 w-100">
					<label htmlFor="basicInput">Key Type</label>

					<div className="position-relative">
						<ClaySelect className="cp-generate-key-select w-100">
							{options.map((option) => (
								<ClaySelect.Option
									key={option.value}
									label={option.label}
								/>
							))}
						</ClaySelect>

						<ClayIcon
							className="select-icon"
							symbol="caret-bottom"
						/>
					</div>
				</div>

				<div>
					<div className="cp-generate-key-select mb-3 mt-5">
						<h5>Subscription</h5>
					</div>

					<div className="cp-generate-key-select mb-2 mt-3">
						{options.map((option) => (
							<Radio
								description="Key activation available: 1 of 2"
								key={option.value}
								label={option.label}
								onChange={(event) =>
									setSelected(event.target.value)
								}
								selected={selected === option.value}
								value={option.value}
							/>
						))}
					</div>

					<div className="dropdown-divider mt-3"></div>
				</div>
			</div>
		</Layout>
	);
};

export default GenerateNewDXPKey;
