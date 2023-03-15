import React from 'react';

export const OAuthUpgradeWarningContext = React.createContext<{
	showOAuthUpgradeWarning: boolean;
	setShowOAuthUpgradeWarning?: (value: boolean) => void;
}>(null);
