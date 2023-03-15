@component_Individuals @team_FARO @priority_3
Feature: Individuals interest details has a tab that shows inactive pages
	As a Business User, I should be able to see a tab with inactive pages

	Background: [Setup] Navigate to Individuals page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Inactive pages shown for individuals
		Given I click the "Known Individuals" tab
		And I click "Abram Bauch" in the table
		And I click the "Interests" tab
		When I click "mesh" in the table
		And I click the "Inactive Pages" tab
		Then I should see text saying "There are no Pages found." on the page