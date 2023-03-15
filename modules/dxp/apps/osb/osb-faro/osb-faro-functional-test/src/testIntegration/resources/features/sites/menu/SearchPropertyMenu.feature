@spira_Properties @Property @Search @team_FARO @priority_4
Feature: Search the Property Menu
	As an Business User, I should be able to search for a Property in the Property Menu

	Background: [Setup] Add a Property
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Properties" page
		* I create a new Property named "SearchPropertyMenu - ${Random.1}"

	Scenario: Search for a Property in the Property Menu
		Given I go to the "Sites" page
		And I click the Property Dropdown Menu
		When I search for "SearchPropertyMenu - ${Random.1}"
		Then I should see the following Properties in the Property Dropdown Menu:
			| SearchPropertyMenu - ${Random.1} |
		And I should not see the following Properties in the Property Dropdown menu:
			| LIFERAY-DATASOURCE-FARO-EXAMPLE |