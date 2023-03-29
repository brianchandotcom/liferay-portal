@spira_Users @List @team_FARO @priority_3
Feature: Assert the User Management Page
	As an Business User, I should be able to see users in the User Management Page

	Background: [Setup]
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page

	Scenario: Assert the User Management Page
		Given I go to the "User Management" page
		Then I should see "test@liferay.com" in a table row containing "Test Test"