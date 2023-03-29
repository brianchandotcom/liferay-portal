import Button from 'shared/components/Button';
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
		<Button
			className='mr-3'
			display='primary'
			onClick={() => onAccept({emailAddress, id})}
			size='sm'
		>
			{Liferay.Language.get('accept')}
		</Button>

		<Button onClick={() => onDecline({emailAddress, id})} size='sm'>
			{Liferay.Language.get('decline')}
		</Button>
	</td>
);

export default RequestActionsRenderer;
