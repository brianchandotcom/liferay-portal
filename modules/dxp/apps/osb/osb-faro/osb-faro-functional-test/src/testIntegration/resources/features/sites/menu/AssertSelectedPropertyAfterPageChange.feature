@spira_Properties @Property @team_FARO @priority_4
Feature: Assert the selected Property after page change
	As an Business User, I should see that the dashboard shows the currently selected Property even after page changes

	Background: [Setup] Add a Property
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Properties" page
		* I create a new Property named "AssertSelectedPropertyAfterPageChange - ${Random.1}"

	Scenario: Select a Property from the Property Dropdown Menu in the Sidebar
		Given I go to the "Sites" page
		And I click the Property Dropdown Menu
		And I click "AssertSelectedPropertyAfterPageChange - ${Random.1}" in the Property Dropdown Menu
		And I should see that "AssertSelectedPropertyAfterPageChange - ${Random.1}" is the current active property
		When I go to the "Segments" page
		Then I should see that "AssertSelectedPropertyAfterPageChange - ${Random.1}" is the current active property
		When I go to the "Individuals" page
		Then I should see that "AssertSelectedPropertyAfterPageChange - ${Random.1}" is the current active property
		When I go to the "Accounts" page
		Then I should see that "AssertSelectedPropertyAfterPageChange - ${Random.1}" is the current active property