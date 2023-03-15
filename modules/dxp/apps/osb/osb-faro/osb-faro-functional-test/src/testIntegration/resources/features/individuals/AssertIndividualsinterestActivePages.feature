@component_Individuals @team_FARO @priority_3
Feature: Individuals interest details has a tab that shows active pages
	As a Business User, I should be able to see a tab with active pages

	Background: [Setup] Navigate to Individuals page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Active pages shown for individuals
		Given I click the "Known Individuals" tab
		And I click "Abram Bauch" in the table
		And I click the "Interests" tab
		When I click "mesh" in the table
		Then I should see page named "mesh synergistic schemas" in the table