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

interface ModalFormatedInformationProps {
	className?: string;
	information?: string;
	label: string;
}

const ModalFormatedInformation = ({
	className = '',
	information,
	label,
}: ModalFormatedInformationProps) => (
	<div className={className}>
		<h2 className="text-paragraph">{label}</h2>

		{information && (
			<p className="align-items-center bg-neutral-1 m-0 p-2 rounded text-paragraph-lg">
				{information}
			</p>
		)}
	</div>
);

export default ModalFormatedInformation;
