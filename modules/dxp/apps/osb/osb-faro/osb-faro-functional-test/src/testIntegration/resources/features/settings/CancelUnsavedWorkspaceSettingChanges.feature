@spira_Workspace @Workspace @Settings @team_FARO @priority_3
Feature: Cancel Unsaved Workspace Setting Changes
	As an Business User, I should be able to cancel unsaved Workspace setting changes

	Background: [Setup] Navigate to Workspace Page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Cancel Unsaved Workspace Setting Changes
		Given I go to the "Workspace" page
		And I type "CancelWorkspaceChanges" into the "Workspace Name" input field
		When I click the "Cancel" button
		Then I should see "FARO-DEV-liferay" in the "Workspace Name" input field