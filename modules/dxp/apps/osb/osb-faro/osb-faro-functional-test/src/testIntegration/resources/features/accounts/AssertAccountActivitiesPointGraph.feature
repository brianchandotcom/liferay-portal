@spira_Accounts @Accounts @Activities @team_FARO @priority_3
Feature: Select point on account activities graph
	As a Business User, I should be able to select point on graph and it shows activities list

	Background: [Setup] Navigate to Segments
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
		* I click "Schowalter-Wisozk" in the table

	Scenario: Select point in accounts activities
		Given I click the "Activities" tab
		When I click row number "1" in the bar graph table
		Then I should see an element exists in the table