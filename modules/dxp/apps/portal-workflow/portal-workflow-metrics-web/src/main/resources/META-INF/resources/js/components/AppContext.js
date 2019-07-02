import React from 'react';

export const AppContext = React.createContext();

export const AppStatus = {
	slaDeleted: 'sla-deleted',
	slaSaved: 'sla-saved',
	slaUpdated: 'sla-updated'
};
