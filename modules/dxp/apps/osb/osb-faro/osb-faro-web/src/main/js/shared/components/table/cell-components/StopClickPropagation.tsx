import React from 'react';

const StopClickPropagation = ({children}) => (
	/* We are disabling the following rules as we don't actually
					want this elment to be explicitly interactable. It only
					serves to stop the propagation of the event to prevent
					the row from being selected.*/

	/* eslint-disable
					jsx-a11y/no-noninteractive-element-interactions,
					jsx-a11y/click-events-have-key-events,
					jsx-a11y/no-static-element-interactions */
	<span onClick={event => event.stopPropagation()}>{children}</span>
);
/* eslint-enable
			jsx-a11y/no-noninteractive-element-interactions,
			jsx-a11y/click-events-have-key-events,
			jsx-a11y/no-static-element-interactions */

export default StopClickPropagation;
