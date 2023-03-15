@spira_Properties @Property @team_FARO @priority_4
Feature: Select a Property from the Property Dropdown Menu in the Sidebar
	As an Business User, I should be able to select a Property from the dropdown menu in the sidebar

	Background: [Setup] Add a Property
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Properties" page
		* I create a new Property named "SelectPropertyMenuItem - ${Random.1}"
		* I go to the "Properties" page
		* I create a new Property named "AnotherProperty - ${Random.1}"

	Scenario: Select a Property from the Property Dropdown Menu in the Sidebar
		Given I go to the "Sites" page
		And I click the Property Dropdown Menu
		And I should see the following Properties in the Property Dropdown Menu:
			| SelectPropertyMenuItem - ${Random.1} |
			| AnotherProperty - ${Random.1}        |
		When I click "SelectPropertyMenuItem - ${Random.1}" in the Property Dropdown Menu
		Then I should see that "SelectPropertyMenuItem - ${Random.1}" is the current active property