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

import Button from '../../../../../common/components/Button';

const AlreadySubmittedFormModal = ({setVisibleModal, submittedModalTexts}) => {
	return (
		<div className="pt-4 px-4">
			<div className="flex-row mb-2">
				<header className="mb-5">
					<h2 className="mb-1 text-neutral-10">
						{submittedModalTexts.title}
					</h2>

					<p className="text-neutral-7 text-paragraph-sm">
						{submittedModalTexts.subtitle}
					</p>
				</header>

				<h5 className="my-1 text-neutral-10">
					{submittedModalTexts.text}
				</h5>

				<p className="mb-5 text-neutral-10">
					{submittedModalTexts.paragraph}
				</p>
			</div>

			<div className="d-flex justify-content-center mb-4 mt-5">
				<Button
					className="px-3 py-2"
					displayType="primary"
					onClick={() => setVisibleModal(false)}
				>
					Done
				</Button>
			</div>
		</div>
	);
};

export default AlreadySubmittedFormModal;
