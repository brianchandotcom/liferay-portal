import React from 'react';

interface ICurrentStatusProps {
	className?: string;
	data: {
		currentMember: boolean;
	};
}

const CurrentStatus: React.FC<ICurrentStatusProps> = ({
	className,
	data: {currentMember}
}) => (
	<td className={className}>
		{currentMember
			? Liferay.Language.get('member')
			: Liferay.Language.get('non-member')}
	</td>
);

export default CurrentStatus;
