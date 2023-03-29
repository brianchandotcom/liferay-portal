import Button from 'shared/components/Button';
import CollapsibleOverlay from 'shared/components/CollapsibleOverlay';
import React, {useState} from 'react';

export default () => {
	const [visible, setVisible] = useState(true);

	return (
		<div style={{height: '500px', position: 'relative'}}>
			<Button onClick={() => setVisible(true)}>
				{'toggle collapsible'}
			</Button>

			<CollapsibleOverlay
				onClose={() => setVisible(false)}
				title='Collapsible Title'
				visible={visible}
			>
				<div style={{padding: '16px'}}>{'Collapsible Child'}</div>
			</CollapsibleOverlay>
		</div>
	);
};
