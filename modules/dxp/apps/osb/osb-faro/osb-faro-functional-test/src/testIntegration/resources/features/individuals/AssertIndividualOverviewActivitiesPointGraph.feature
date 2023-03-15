@spira_Individuals @component_Individuals @team_FARO @priority_3
Feature: Select point on Individual Overview activities graph
	As a Business User, I should be able to select a point on individual overview activities graph

	Background: [Setup] Navigate to Individuals page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Select point on individual overview activities
		Given I click the "Known Individuals" tab
		And I click "Abram Bauch" in the table
		When I click row number "30" in the bar graph table
		Then I should see an element exists in the table