@spira_Properties @Property @team_FARO @priority_4
Feature: Assert the Property Menu after deleting a Property
	As an Business User, I should not see a deleted Property in the Property Menu

	Background: [Setup] Add a Property
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Properties" page
		* I create a new Property named "AssertPropertyMenuAfterDelete - ${Random.1}"

	Scenario: Assert the Property Menu after deleting a Property
		Given I go to the "Properties" page
		When I delete the "AssertPropertyMenuAfterDelete - ${Random.1}" Property
		And I go to the "Sites" page
		And I click the Property Dropdown Menu
		Then I should not see the following Properties in the Property Dropdown Menu:
			| AssertPropertyMenuAfterDelete - ${Random.1} |