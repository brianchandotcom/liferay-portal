const steps = {
	dxpCloud: 2,
	invites: 1,
	successDxpCloud: 3,
	welcome: 0,
};

const roles = {
	ADMIN: {
		key: 'Account Administrator',
		name: 'Administrator',
	},
	MEMBER: {
		key: 'Account Member',
		name: 'User',
	},
	PARTNER_MANAGER: {
		key: 'Partner Manager',
		name: 'Partner Manager',
	},
	PARTNER_MEMBER: {
		key: 'Partner Member',
		name: 'Partner Member',
	},
	REQUESTOR: {
		key: 'Requestor',
		name: 'Requestor',
	},
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
