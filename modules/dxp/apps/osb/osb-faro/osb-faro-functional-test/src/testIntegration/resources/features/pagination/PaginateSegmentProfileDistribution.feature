@spira_Pagination @Pagination @Segments @List @Profile @team_FARO @priority_3
Feature: List of individuals can be paginated in distribution
	As a Business User, I should be able to paginate to the second page in distribution tab

	Background: [Setup] Navigate to Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click "everybody" in the table

	Scenario: Paginate to second page in Distribution tab
		Given I click the "Distribution" tab
		And I select breakdown "Individuals" by "gender"
		And I click row number "1" in the bar graph table
		When I go to page "2" in the creation modal
		Then I should be on page "2" in the creation modal
		And I should see an individual named "Larae Howe" in the table