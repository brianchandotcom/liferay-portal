const steps = {
	dxpCloud: 2,
	invites: 1,
	successDxpCloud: 3,
	welcome: 0,
};

const roles = {
	ADMIN: {
		id: 'Account Administrator',
		name: 'Administrator',
	},
	MEMBER: {
		id: 'Account Member',
		name: 'User',
	},
	PARTNER_MANAGER: {
		id: 'Partner Manager',
		name: 'Partner Manager',
	},
	PARTNER_MEMBER: {
		id: 'Partner Member',
		name: 'Partner Member',
	},
	REQUESTOR: {
		id: 'Requestor',
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
