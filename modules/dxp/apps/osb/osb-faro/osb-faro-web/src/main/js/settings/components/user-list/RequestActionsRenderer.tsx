import ClayButton from '@clayui/button';
import React from 'react';

interface IRequestActionsRendererProps
	extends React.HTMLAttributes<HTMLTableCellElement> {
	data: {[key: string]: any};
	onAccept: ({emailAddress, id}) => any;
	onDecline: ({emailAddress, id}) => any;
}

const RequestActionsRenderer: React.FC<IRequestActionsRendererProps> = ({
	className,
	data: {emailAddress, id},
	onAccept,
	onDecline
}) => (
	<td className={className}>
		<ClayButton
			className='button-root mr-3'
			displayType='primary'
			onClick={() => onAccept({emailAddress, id})}
			size='sm'
		>
			{Liferay.Language.get('accept')}
		</ClayButton>

		<ClayButton
			className='button-root'
			displayType='secondary'
			onClick={() => onDecline({emailAddress, id})}
			size='sm'
		>
			{Liferay.Language.get('decline')}
		</ClayButton>
	</td>
);

export default RequestActionsRenderer;
