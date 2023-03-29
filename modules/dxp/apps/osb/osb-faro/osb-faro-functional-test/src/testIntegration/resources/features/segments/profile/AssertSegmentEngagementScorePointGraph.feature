@spira_Segments @Segments @Profile @team_FARO @priority_3
Feature: Select point on engagement score graph shows individuals
	As a Business User, I should be able to select a point on the graph and it shows individuals

	Background: [Setup] Navigate to Segments
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click "engineers" in the table

	Scenario: Select point on engagement score graph
		Given I click the "Membership" tab
		And I click the "Segment Engagement Score" card tab
		When I click row number "1" in the bar graph table
		Then I should see an element exists in the table