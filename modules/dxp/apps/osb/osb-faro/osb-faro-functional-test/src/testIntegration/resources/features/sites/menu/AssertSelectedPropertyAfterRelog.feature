@spira_Properties @Property @team_FARO @priority_3
Feature: Assert the last selected Property is selected after re-logging
	As an Business User, I should see that the first Property is selected after logging in

	Background: [Setup] Add a Property
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Properties" page
		* I create a new Property named "AssertSelectedPropertyAfterRelog - ${Random.1}"
		
	Scenario: Assert the last selected Property is selected after re-logging
		Given I go to the "Sites" page
		And I click the Property Dropdown Menu
		And I click "AssertSelectedPropertyAfterRelog - ${Random.1}" in the Property Dropdown Menu
		When I click the name of the signed in user
		And I click the "Sign Out" User Menu option
		And I login as "test@faro.io:test"
		And I should see the "Sites" page
		Then I should see that "AssertSelectedPropertyAfterRelog - ${Random.1}" is the current active Property