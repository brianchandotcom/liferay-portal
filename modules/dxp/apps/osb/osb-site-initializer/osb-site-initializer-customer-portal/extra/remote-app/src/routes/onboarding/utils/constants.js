const steps = {
	dxpCloud: 2,
	invites: 1,
	successDxpCloud: 3,
	welcome: 0,
};

const roles = {
	ADMIN: 'Account Administrator',
	MEMBER: 'Account Member',
	PARTNER_MANAGER: 'Partner Manager',
	PARTNER_MEMBER: 'Partner Manager',
	REQUESTOR: 'Requestor',
};

const getInitialDxpAdmin = () => ({
	email: '',
	firstName: '',
	github: '',
	lastName: '',
});

const getInitialInvite = (roleId = '') => ({
	email: '',
	roleId,
});

export {steps, getInitialInvite, getInitialDxpAdmin, roles};
