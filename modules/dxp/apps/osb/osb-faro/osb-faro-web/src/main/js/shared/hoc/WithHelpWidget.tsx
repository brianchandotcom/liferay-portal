import HelpWidget from 'shared/components/HelpWidget';
import React from 'react';
import {Modal} from 'shared/types';

interface IWrappedComponentProps {
	close: Modal.close;
	currentUserId: string;
	faroSubscriptionIMap: Map<string, any>;
	groupId: string;
	open: Modal.open;
	serverLocation: string;
	workspaceName: string;
}

const withHelpWidget = (
	WrappedComponent: React.ComponentType<IWrappedComponentProps>
) => props => (
	<>
		<WrappedComponent {...props} />

		<HelpWidget groupId={props.groupId} />
	</>
);

export default withHelpWidget;
