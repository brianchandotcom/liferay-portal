@spira_Segments @Segments @Profile @team_FARO @priority_3
Feature: Select point on membership shows individuals added/removed
	As a Business User, I should be able to select point on the graph and it shows individuals added/removed

	Background: [Setup] Navigate to Segments
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click "engineers" in the table

	Scenario: Select point on membership graph
		Given I click the "Membership" tab
		When I click row number "1" in the bar graph table
		Then I should see an element exists in the table