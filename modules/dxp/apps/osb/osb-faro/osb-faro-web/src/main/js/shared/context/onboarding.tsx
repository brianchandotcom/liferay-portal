import React from 'react';

export const OnboardingContext = React.createContext<{
	onboardingTriggered: boolean;
	setOnboardingTriggered: () => void;
}>(null);
