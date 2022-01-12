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

import ClayTable from '@clayui/table';

const CommerceTable = () => {
	return (
		<>
			<div>
				<h1 className="mb-5 text-neutral-10">Activation Keys</h1>
			</div>
			<ClayTable>
				<ClayTable.Head>
					<ClayTable.Row>
						<ClayTable.Cell
							className="bg-neutral-2 pl-4 py-3 text-neutral-8"
							expanded
							headingCell
						>
							Version
						</ClayTable.Cell>

						<ClayTable.Cell
							className="bg-neutral-2 py-3 text-neutral-8"
							headingCell
						>
							Instructions
						</ClayTable.Cell>
					</ClayTable.Row>
				</ClayTable.Head>

				<ClayTable.Body>
					<ClayTable.Row>
						<ClayTable.Cell className="pl-4 py-0" headingTitle>
							<p className="mb-0 py-4 text-neutral-7">
								DXP 7.4 GA1+
							</p>
						</ClayTable.Cell>

						<ClayTable.Cell className="text-neutral-9 text-paragraph">
							All Commerce modules are enabled by default.
						</ClayTable.Cell>
					</ClayTable.Row>

					<ClayTable.Row>
						<ClayTable.Cell className="pl-4 py-0" headingTitle>
							<p className="mb-0 py-4 text-neutral-7">
								DXP 7.3 FP3/SP2+
							</p>
						</ClayTable.Cell>

						<ClayTable.Cell>
							<div>
								<p className="mb-0 text-neutral-9 text-paragraph">
									Commerce is activated using a portal
									property.
								</p>

								<p className="mb-0 text-neutral-7 text-paragraph-sm">
									More details:
									<a className="text-neutral-7">
										<u> Activating Liferay Commerce</u>
									</a>
								</p>
							</div>
						</ClayTable.Cell>
					</ClayTable.Row>

					<ClayTable.Row>
						<ClayTable.Cell className="pl-4 py-0" headingTitle>
							<p className="mb-0 py-4 text-neutral-7">
								DXP 7.3 FP2/SP1
							</p>
						</ClayTable.Cell>

						<ClayTable.Cell>
							<div>
								<p className="mb-0 text-neutral-9 text-paragraph">
									Commerce requires an activation key.
								</p>

								<p className="mb-0 text-neutral-7 text-paragraph-sm">
									To request a new or replacement activation
									key, please
									<a className="text-neutral-7">
										<u> open a support ticket.</u>
									</a>
								</p>
							</div>
						</ClayTable.Cell>
					</ClayTable.Row>
				</ClayTable.Body>
			</ClayTable>
		</>
	);
};

export default CommerceTable;
